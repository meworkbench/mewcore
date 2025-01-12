package pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.io;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.readers.JSBMLReader;
import pt.uminho.ceb.biosystems.mew.core.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.core.model.components.ReactionConstraint;
import pt.uminho.ceb.biosystems.mew.core.model.converters.ContainerConverter;
import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.configuration.GenericOptimizationProperties;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.controlcenter.StrainOptimizationControlCenter;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.IObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.objectivefunctions.ofs.FluxValueObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.IStrainOptimizationResultSet;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.solutionset.RKSolutionSet;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.strainoptimizationalgorithms.jecoli.components.strategyconverter.JecoliGKConverter;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.strainoptimizationalgorithms.jecoli.ea.JecoliEACSOMConfig;
import pt.uminho.ceb.biosystems.mew.utilities.datastructures.map.indexedhashmap.IndexedHashMap;
import pt.uminho.ceb.biosystems.mew.utilities.datastructures.pair.Pair;

/**
 * Created by ptiago on 31-03-2015.
 */
public class RKStrategyReaderTest extends AbstractStrategyReader {
    @Test
    public void readSolutionFile() throws Exception {
        List<Pair<Double,List<Pair<String,Double>>>>  originalSolutionList =createOriginalSolutionList();
        StrainOptimizationControlCenter cc = new StrainOptimizationControlCenter();
        JecoliEACSOMConfig config = new JecoliEACSOMConfig();
        config.setProperty(GenericOptimizationProperties.OPTIMIZATION_ALGORITHM,"SA");
        String resultFileLocation = "./TestData/RK.data";
        String modelFile = "./TestModel/iMM904_Methylglyoxal.xml";
        JSBMLReader modelReader = new JSBMLReader(modelFile,"PT",false);
        Container container = new Container(modelReader);
        List<String> notAllowedIDs = new ArrayList<>();
        IndexedHashMap<IObjectiveFunction,String> objectiveMap = new IndexedHashMap<>();
        objectiveMap.put(new FluxValueObjectiveFunction("R_biomass_published",true),"FBA");
        container.verifyDepBetweenClass();
        ISteadyStateModel model = ContainerConverter.convert(container);
        EnvironmentalConditions env = new EnvironmentalConditions();
        env.addReactionConstraint("R_EX_glc_e_",new ReactionConstraint(-10,0));
        config.setModel(model);
        config.setMaxSetSize(2);
        config.setNotAllowedIds(notAllowedIDs);
        config.setOptimizationStrategy("RK");
        config.setOptimizationStrategyConverter(new JecoliGKConverter<>());
        config.setIsVariableSizeGenome(true);
//        config.setEnvironmentalConditions(env);
//        config.setSolver(SolverType.CPLEX3);
        List<String> simulationMethodList = new ArrayList<>();
        simulationMethodList.add("FBA");
//        config.setSimulationMethod(simulationMethodList);
//        config.setIsMaximization(true);
//        config.setMapOF2Sim(objectiveMap);
//        config.setOu2StepApproach(true);

        IStrainOptimizationResultSet newResultSet = new RKSolutionSet(config);
        newResultSet.readSolutionsFromFile(resultFileLocation);
        assertTrue(validateSolution(false,originalSolutionList,newResultSet));
    }

    protected List<Pair<Double, List<Pair<String, Double>>>> createOriginalSolutionList() {
        final Pair<Double,List<Pair<String, Double>>> solutionPair = new Pair<Double,List<Pair<String, Double>>>(0.9105604168722893,
                new ArrayList<Pair<String,Double>>(){{
                    add(new Pair<String,Double>("R_EX_thm_e_",0.0));}});

        final Pair<Double,List<Pair<String, Double>>> solutionPair1 = new Pair<Double,List<Pair<String, Double>>>(0.9105603876884507,
                new ArrayList<Pair<String,Double>>(){{
                    add(new Pair<String,Double>("R_THMPe",0.0));}});

        final Pair<Double,List<Pair<String, Double>>> solutionPair2 = new Pair<Double,List<Pair<String, Double>>>(0.9105604152094574,
                new ArrayList<Pair<String,Double>>(){{
                    add(new Pair<String,Double>("R_T_R_ETOHtm",0.0));}});

        return new ArrayList<Pair<Double, List<Pair<String, Double>>>>(){{
            add(solutionPair);
            add(solutionPair1);
            add(solutionPair2);
        }};
    }
}
