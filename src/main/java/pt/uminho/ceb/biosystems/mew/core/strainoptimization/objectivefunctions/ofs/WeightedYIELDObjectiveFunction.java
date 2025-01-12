package pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.ofs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.core.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.core.model.components.ReactionConstraint;
import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.GeneticConditions;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationProperties;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationSteadyStateControlCenter;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SteadyStateSimulationResult;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.AbstractObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.InvalidObjectiveFunctionConfiguration;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.ObjectiveFunctionParameterType;
import pt.uminho.ceb.biosystems.mew.core.utils.Debugger;

public class WeightedYIELDObjectiveFunction extends AbstractObjectiveFunction {
	
	private static final long serialVersionUID = 1L;
	
	public static final String	ID								= "WYIELD";
	public static final String	WYIELD_PARAM_BIOMASS			= "Biomass";
	public static final String	WYIELD_PARAM_PRODUCT			= "Product";
	public static final String	WYIELD_PARAM_ALPHA				= "Alpha";
	public static final String	WYIELD_PARAM_SOLVER				= "Solver";
	public static final String	WYIELD_PARAM_MIN_BIOMASS_VALUE	= "MinBiomassValue";
	protected final double		worstFitness					= 0.0d;
	
	transient protected SimulationSteadyStateControlCenter center = null;
	
	public Map<String, ObjectiveFunctionParameterType> loadParameters(){
		Map<String, ObjectiveFunctionParameterType> myparams = new HashMap<>();
		myparams.put(WYIELD_PARAM_BIOMASS, ObjectiveFunctionParameterType.REACTION_BIOMASS);
		myparams.put(WYIELD_PARAM_PRODUCT, ObjectiveFunctionParameterType.REACTION_PRODUCT);
		myparams.put(WYIELD_PARAM_ALPHA, ObjectiveFunctionParameterType.DOUBLE);
		myparams.put(WYIELD_PARAM_SOLVER, ObjectiveFunctionParameterType.SOLVER);
		myparams.put(WYIELD_PARAM_MIN_BIOMASS_VALUE, ObjectiveFunctionParameterType.DOUBLE);
		return Collections.unmodifiableMap(myparams);
	}
	
	public WeightedYIELDObjectiveFunction(){super();}
	
	public WeightedYIELDObjectiveFunction(Map<String, Object> configuration) throws InvalidObjectiveFunctionConfiguration {
		super(configuration);
	}
	
	public WeightedYIELDObjectiveFunction(String biomassId, String desiredFluxId, Double alpha, String lpSolver, Double minBiomassValue) {
		super(biomassId, desiredFluxId, alpha, lpSolver, minBiomassValue);
	}
	
	@Override
	protected void processParams(Object... params) {
		setParameterValue(WYIELD_PARAM_BIOMASS, params[0]);
		setParameterValue(WYIELD_PARAM_PRODUCT, params[1]);
		setParameterValue(WYIELD_PARAM_ALPHA, params[2]);
		setParameterValue(WYIELD_PARAM_SOLVER, params[3]);
		setParameterValue(WYIELD_PARAM_MIN_BIOMASS_VALUE, params[4]);
	}
	
	@Override
	public double evaluate(SteadyStateSimulationResult simResult) {
		
		String biomassId = (String) getParameterValue(WYIELD_PARAM_BIOMASS);
		String desiredFluxId = (String) getParameterValue(WYIELD_PARAM_PRODUCT);
		double alpha = (double) getParameterValue(WYIELD_PARAM_ALPHA);
		String lpSolver = (String) getParameterValue(WYIELD_PARAM_SOLVER);
		double minBiomassValue = (double) getParameterValue(WYIELD_PARAM_MIN_BIOMASS_VALUE);
		
		double fvaMaxProd = 0;
		double fvaMinProd = 0;
		double biomassFluxValue = simResult.getFluxValues().getValue(biomassId) * 0.99999;
		double fbaTargetValue = simResult.getFluxValues().getValue(desiredFluxId);
		ISteadyStateModel model = simResult.getModel();
		
		GeneticConditions gc = simResult.getGeneticConditions();
		
		EnvironmentalConditions ec = new EnvironmentalConditions();
		if (simResult.getEnvironmentalConditions() != null) ec.putAll(simResult.getEnvironmentalConditions());
		ec.addReactionConstraint(biomassId, new ReactionConstraint(biomassFluxValue, 100000.0));
		
		if (center == null) {
			center = new SimulationSteadyStateControlCenter(null, null, model, SimulationProperties.FBA);
			center.setSolver(lpSolver);
			center.setFBAObjSingleFlux(desiredFluxId, 1.0);
			if(gc.isOverUnder())
				center.setOverUnder2StepApproach(true);
		}
		
		center.setGeneticConditions(gc);
		center.setEnvironmentalConditions(ec);
		center.setMaximization(true);
		SteadyStateSimulationResult fvaMaxResult = null;
		
		try {
//			System.out.println("EVALUATION FVA MAX!!!");
			fvaMaxResult = (SteadyStateSimulationResult) center.simulate();
//			System.out.println("EVALUATION FVA MAX DONE!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (fvaMaxResult != null && fvaMaxResult.getFluxValues() != null) fvaMaxProd = fvaMaxResult.getFluxValues().getValue(desiredFluxId);
		
		if (fvaMaxProd > 0.00000001) {
			center.setMaximization(false);
			SteadyStateSimulationResult fvaMinResult = null;
			
			try {
				fvaMinResult = (SteadyStateSimulationResult) center.simulate();
//								center.forceSolverCleanup();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (fvaMinResult != null && fvaMinResult.getFluxValues() != null) fvaMinProd = fvaMinResult.getFluxValues().getValue(desiredFluxId);
		}
		
		double ret = worstFitness;
		if (biomassFluxValue > minBiomassValue) ret = (alpha * fvaMaxProd + (1 - alpha) * fvaMinProd);
		Debugger.debug("bx = " + biomassFluxValue + "\t fba tr (" + desiredFluxId + ") = " + fbaTargetValue + "\t fvaMax = " + fvaMaxProd + "\t fvaMin = " + fvaMinProd + "\t of = " + ret + "\talpha = " + alpha);
		return ret;
	}
	
	@Override
	public double getWorstFitness() {
		return worstFitness;
	}
	
	@Override
	public boolean isMaximization() {
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * metabolic.optimization.objectivefunctions.interfaces.IObjectiveFunction#
	 * getUnnormalizedFitness(double)
	 */
	@Override
	public double getUnnormalizedFitness(double fit) {
		return fit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * metabolic.optimization.objectivefunctions.interfaces.IObjectiveFunction#
	 * getShortString()
	 */
	@Override
	public String getShortString() {
		return "WYIELD~" + getParameterValue(WYIELD_PARAM_PRODUCT) + "~" + getParameterValue(WYIELD_PARAM_ALPHA) + "~" + getParameterValue(WYIELD_PARAM_MIN_BIOMASS_VALUE);
	}
	
	public String getDesiredId() {
		return (String) getParameterValue(WYIELD_PARAM_PRODUCT);
	}
	
	@Override
	public String getLatexString() {
		return "$" + getLatexFormula() + "$";
	}
	
	@Override
	public String getLatexFormula() {
		return "WeigthedYIELD = " + "\\alpha \\times FVA_{max}\\;\\text{" + getParameterValue(WYIELD_PARAM_PRODUCT) + "} + " + "(1-\\alpha) \\times FVA_{min}\\;\\text{" + getParameterValue(WYIELD_PARAM_PRODUCT) + "}";
	}
	
	@Override
	public String getBuilderString() {
		return "WYIELD(" + getParameterValue(WYIELD_PARAM_BIOMASS) + "," + getParameterType(WYIELD_PARAM_PRODUCT) + "," + getParameterValue(WYIELD_PARAM_ALPHA) + "," + getParameterValue(WYIELD_PARAM_SOLVER) + ","
				+ getParameterValue(WYIELD_PARAM_MIN_BIOMASS_VALUE) + ")";
	}
	
	@Override
	public String getID() {
		return ID;
	}
	
}
