package pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.solution;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.mewcore.optimization.objectivefunctions.interfaces.IObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.GeneChangesList;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.GeneticConditions;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.SteadyStateSimulationResult;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.AbstractStrainOptimizationResult;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.strainoptimizationalgorithms.jecoli.JecoliGenericConfiguration;
import pt.uminho.ceb.biosystems.mew.utilities.datastructures.map.indexedhashmap.IndexedHashMap;
import pt.uminho.ceb.biosystems.mew.utilities.java.StringUtils;

/**
 * Created by ptiago on 18-03-2015.
 */
public class GKSolution extends AbstractStrainOptimizationResult<JecoliGenericConfiguration> {
    public GKSolution(JecoliGenericConfiguration configuration, Map<String, SteadyStateSimulationResult> simulationResultMap, GeneticConditions solutionGeneticConditions) {
        super(configuration, simulationResultMap, solutionGeneticConditions);
    }

    public GKSolution(JecoliGenericConfiguration baseConfiguration, GeneticConditions gc) {
        super(baseConfiguration, gc);
    }

    @Override
    public void write(OutputStreamWriter outputStream) throws Exception {
        GeneChangesList geneChangesList = solutionGeneticConditions.getGeneList();
        List<String> geneKnockoutList = geneChangesList.getGeneKnockoutList();
        IndexedHashMap<IObjectiveFunction,String> mapOf2SimMap = configuration.getMapOf2Sim();
        writeMapOf2SimMap(outputStream,mapOf2SimMap);

        for(String geneKnockout:geneKnockoutList)
            outputStream.write(","+geneKnockout);

        StringUtils.concat(",",geneKnockoutList);

        outputStream.write("\n");
    }
}