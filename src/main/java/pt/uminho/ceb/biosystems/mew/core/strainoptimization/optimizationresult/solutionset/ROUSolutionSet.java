package pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.solutionset;

import java.util.List;

import pt.uminho.ceb.biosystems.mew.core.simulation.components.GeneticConditions;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.AbstractStrainOptimizationResultSet;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.IStrainOptimizationReader;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.io.rou.ROUStrategyReader;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.optimizationresult.solution.ROUSolution;
import pt.uminho.ceb.biosystems.mew.core.strainoptimization.strainoptimizationalgorithms.jecoli.JecoliGenericConfiguration;

/**
 * Created by ptiago on 18-03-2015.
 */
public class ROUSolutionSet <T extends  JecoliGenericConfiguration> extends AbstractStrainOptimizationResultSet<T,ROUSolution> {

	private static final long	serialVersionUID	= 1L;

	public ROUSolutionSet(T baseConfiguration, List<ROUSolution> resultList) {
        super(baseConfiguration, resultList);
    }

    public ROUSolutionSet(T baseConfiguration) {
        super(baseConfiguration);
    }

    @Override
    public ROUSolution createSolution(GeneticConditions gc) {
    	return new ROUSolution(gc);
    }

	@Override
	public IStrainOptimizationReader getSolutionReaderInstance() {
		return new ROUStrategyReader();
	}
}
