package pt.uminho.ceb.biosystems.mew.mewcore.metabolic.simulation.cplex;

import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.solvers.SolverType;

import pt.uminho.ceb.biosystems.mew.mewcore.metabolic.simulation.abstracts.AbstractSimulationTest;
import pt.uminho.ceb.biosystems.mew.mewcore.metabolic.simulation.abstracts.FBASimulationTest;
import pt.uminho.ceb.biosystems.mew.mewcore.metabolic.simulation.abstracts.MiMBlSimulationTest;

public class CPLEXMiMBlSimulationTest extends MiMBlSimulationTest{

	@Override
	public SolverType getSolver() {
		return SolverType.CPLEX;
	}

	@Override
	protected Map<String, Double> getResults() {
		if(results == null){
			results= new HashMap<String, Double>();
			results.put(AbstractSimulationTest.WILDTYPE, -1.067123E-6);
			results.put(AbstractSimulationTest.KO_REACTIONS, 194.81571); 
			results.put(AbstractSimulationTest.KO_GENETICS, 234.46329260143392); //NaN
			results.put(AbstractSimulationTest.UO_REACTIONS, 0.0);
			results.put(AbstractSimulationTest.UO_GENETICS, 214.47306);
			results.put(AbstractSimulationTest.UO_REACTIONSGENETICS, 17.58858797306791);
			results.put(AbstractSimulationTest.KO_REACTIONSGENETICS, 263.6401906898394); //NaN
		}
		return results;
	}
}
