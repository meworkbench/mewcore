package pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization;

/**
 * Created by ptiago on 31-03-2015.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.controlcenter.StrainOptimizationControlCenterTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.io.GKStrategyReaderTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.io.GOUStrategyReaderTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.io.RKRSStrategyReaderTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.io.RKStrategyReaderTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.optimizationresult.io.ROUStrategyReaderTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.strainoptimizationalgorithms.jecoli.JecoliEASCOMConfigTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.strainoptimizationalgorithms.jecoli.JecoliSCOMConfigTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.strainoptimizationalgorithms.jecoli.ea.JecoliEAGeneKnockoutCSOMTest;
import pt.uminho.ceb.biosystems.mew.mewcore.strainoptimization.strainoptimizationalgorithms.jecoli.ea.JecoliEAReactionKnockoutCSOMTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StrainOptimizationControlCenterTest.class,
        GKStrategyReaderTest.class,
        GOUStrategyReaderTest.class,
        RKRSStrategyReaderTest.class,
        RKStrategyReaderTest.class,
        ROUStrategyReaderTest.class,
        JecoliEASCOMConfigTest.class,
        JecoliSCOMConfigTest.class,
        JecoliEAGeneKnockoutCSOMTest.class,
        JecoliEAReactionKnockoutCSOMTest.class
})
public class StrainOptimizationTestSuite {
}