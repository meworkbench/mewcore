package pt.uminho.ceb.biosystems.mew.core.simulation.formulations.abstractions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.solvers.lp.LPConstraint;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPConstraintType;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblem;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblemRow;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPVariable;
import pt.uminho.ceb.biosystems.mew.solvers.lp.exceptions.LinearProgrammingTermAlreadyPresentException;

public class VarTerm extends AbstractObjTerm{

	public VarTerm(int varIndex, double additional) {
		this(varIndex, 1, additional);
	}
	
	public VarTerm(int varIndex) {
		this(varIndex, 1, 0);
	}
	
	public VarTerm(int varIndex, double multiplier, double additional) {
		super(varIndex, multiplier, additional);
	}

	@Override
	public Map<String, Integer> addObjectiveTermToProblem(LPProblem problem) throws WrongFormulationException {
		
		LPObjectiveFunction objective = problem.getObjectiveFunction();
		
		Map<String, Integer> var = new HashMap<String, Integer>();
		int varIndexfo = problem.getNumberVariables();
		String name = "fo_"+varIndex;
		
		var.put(name, varIndexfo);

		LPVariable normFoVar = new LPVariable(name, minValue, maxValue);

		problem.addVariable(normFoVar);
		LPProblemRow rowFo = new LPProblemRow();
		
		try {
			rowFo.addTerm(varIndex, multiplier);
			rowFo.addTerm(varIndexfo, -1);
			LPConstraint constFo = new LPConstraint(LPConstraintType.EQUALITY, rowFo, -additional);
			problem.addConstraint(constFo);
			
			objective.addRow(varIndexfo, 1);
		} catch (LinearProgrammingTermAlreadyPresentException e) {
			throw new WrongFormulationException(e);
		}		
		
		return var;
		
	}

	@Override
	public Map<String, Integer> addObjectiveTermToProblem(LPProblem problem, List<LPVariable> ofAssociatedVars, List<LPConstraint> ofAssociatedConstraints) throws WrongFormulationException {
		LPObjectiveFunction objective = problem.getObjectiveFunction();
		
		Map<String, Integer> var = new HashMap<String, Integer>();
		int varIndexfo = problem.getNumberVariables();
		String name = "fo_"+varIndex;
		
		var.put(name, varIndexfo);
		LPVariable normFoVar = new LPVariable(name, minValue, maxValue);
		problem.addVariable(normFoVar);
		//NEW
		ofAssociatedVars.add(normFoVar);
		LPProblemRow rowFo = new LPProblemRow();
		
		try {
			rowFo.addTerm(varIndex, multiplier);
			rowFo.addTerm(varIndexfo, -1);
			LPConstraint constFo = new LPConstraint(LPConstraintType.EQUALITY, rowFo, -additional);
			//NEW
			ofAssociatedConstraints.add(constFo);
			problem.addConstraint(constFo);
			
			objective.addRow(varIndexfo, 1);
		} catch (LinearProgrammingTermAlreadyPresentException e) {
			throw new WrongFormulationException(e);
		}
		
		
		return var;
	}
	
}
