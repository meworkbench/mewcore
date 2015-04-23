package pt.uminho.ceb.biosystems.mew.mewcore.simulation.mfa2.methods.algebra;

import pt.uminho.ceb.biosystems.mew.solvers.lp.LPSolutionType;

import pt.uminho.ceb.biosystems.mew.mewcore.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.mewcore.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.FluxValueMap;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.GeneticConditions;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.SteadyStateSimulationResult;

public class MFAClassicAlgebraResult extends SteadyStateSimulationResult{

	private static final long serialVersionUID = 8895584356665608252L;
	
	public MFAClassicAlgebraResult(ISteadyStateModel model,
			EnvironmentalConditions environmentalConditions,
			GeneticConditions geneticConditions, 
			String method,
			FluxValueMap fluxValues, 
			String solverOutput, 
			Double oFvalue,
			String oFString, 
			LPSolutionType solutionType) {
		
		super(model, environmentalConditions, geneticConditions, method, fluxValues, solverOutput, oFvalue, oFString, solutionType);
	}

	public MFAClassicAlgebraResult(ISteadyStateModel model, String method, FluxValueMap fluxValues) {
		super(model, method, fluxValues);
	}
	
	@Override
	public double getOFvalue() {
		return Double.NaN;
	}

	@Override
	public String getOFString() {
		return "O.F.";
	}

}
