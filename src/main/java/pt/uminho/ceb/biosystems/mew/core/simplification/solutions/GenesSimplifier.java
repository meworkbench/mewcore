package pt.uminho.ceb.biosystems.mew.core.simplification.solutions;

import java.util.Map;
import java.util.Set;

import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.gpr.ISteadyStateGeneReactionModel;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.GeneticConditions;

public class GenesSimplifier extends AbstractPersistenceSimplifier{
	
	public GenesSimplifier(Map<String,Map<String,Object>> simulationConfiguration) {
		super(simulationConfiguration);
	}

	@Override
	public Set<String> getGeneticConditionsIDs(GeneticConditions gc) {
		return gc.getGeneList().getGeneIds();
	}

	@Override
	public void nextGeneticCondition(GeneticConditions solution, String geneID, double expressionLevel) throws Exception {
		solution.getGeneList().addGene(geneID, expressionLevel);
		solution.updateReactionsList((ISteadyStateGeneReactionModel) model);
	}

	@Override
	public void removeGeneticCondition(GeneticConditions gc, String id) throws Exception {
		gc.getGeneList().removeGene(id);
		gc.updateReactionsList((ISteadyStateGeneReactionModel) model);
	}

	@Override
	public double getExpressionLevel(GeneticConditions gc, String id) {
		return gc.getGeneList().getGeneExpression(id);
	}

}
