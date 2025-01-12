package pt.uminho.ceb.biosystems.mew.core.simulation.formulations;

import pt.uminho.ceb.biosystems.mew.core.model.components.enums.ReactionType;
import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.core.simulation.formulations.abstractions.AbstractSSReferenceSimulation;
import pt.uminho.ceb.biosystems.mew.core.simulation.formulations.abstractions.L1VarTerm;
import pt.uminho.ceb.biosystems.mew.core.simulation.formulations.exceptions.MandatoryPropertyException;
import pt.uminho.ceb.biosystems.mew.core.simulation.formulations.exceptions.PropertyCastException;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblem;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblemRow;

public class NormLMoma extends AbstractSSReferenceSimulation<LPProblem>{
	

	public NormLMoma(ISteadyStateModel model) {
		super(model);
	}

	@Override
	public  LPProblem constructEmptyProblem() {
		return new LPProblem();
	}

	@Override
	protected void createObjectiveFunction() throws PropertyCastException, MandatoryPropertyException  {
		problem.setObjectiveFunction(new LPProblemRow(), false);

		getWTReference();
		boolean useDrains = getUseDrainsInRef();
		for(String id: wtReference.keySet()){
			int idxVar = idToIndexVarMapings.get(id);
			double value = wtReference.get(id);
			
			if(value !=0.0 && (useDrains || !model.getReaction(id).getType().equals(ReactionType.DRAIN)))
				objTerms.add(new L1VarTerm(idxVar,-1/value,1));
		}
	}

	@Override
	public String getObjectiveFunctionToString() {
		return "Σ (1/|wt|) *|v - wt| ";
	}

}
