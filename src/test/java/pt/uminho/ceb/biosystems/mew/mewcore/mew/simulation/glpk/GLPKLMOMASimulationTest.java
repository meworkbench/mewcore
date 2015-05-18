package pt.uminho.ceb.biosystems.mew.mewcore.mew.simulation.glpk;

import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.mewcore.mew.simulation.abstracts.AbstractSimulationTest;
import pt.uminho.ceb.biosystems.mew.mewcore.mew.simulation.abstracts.LMOMASimulationTest;
import pt.uminho.ceb.biosystems.mew.solvers.SolverType;

public class GLPKLMOMASimulationTest extends LMOMASimulationTest{

	@Override
	public SolverType getSolver() {
		return SolverType.GLPK;
	}

	@Override
	protected Map<String, Double> getResults() {
		if(results == null){
			results= new HashMap<String, Double>();
			results.put(AbstractSimulationTest.WILDTYPE, 3.061040818E-4);
			results.put(AbstractSimulationTest.KO_REACTIONS, 285.784218);
			results.put(AbstractSimulationTest.KO_GENETICS, 0.0);
			results.put(AbstractSimulationTest.UO_REACTIONS, 6.019336339E-4);
			results.put(AbstractSimulationTest.UO_GENETICS, 299.0993812);
			results.put(AbstractSimulationTest.UO_REACTIONSGENETICS, 109.881731);
			results.put(AbstractSimulationTest.KO_REACTIONSGENETICS, 0.0);
		}
		return results;
	}
}