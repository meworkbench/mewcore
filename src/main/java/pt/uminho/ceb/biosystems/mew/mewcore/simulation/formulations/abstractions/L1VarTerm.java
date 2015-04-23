package pt.uminho.ceb.biosystems.mew.mewcore.simulation.formulations.abstractions;

import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.solvers.lp.LPConstraint;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPConstraintType;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPObjectiveFunction;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblem;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblemRow;
import pt.uminho.ceb.biosystems.mew.solvers.lp.LPVariable;
import pt.uminho.ceb.biosystems.mew.solvers.lp.exceptions.LinearProgrammingTermAlreadyPresentException;

public class L1VarTerm extends AbstractObjTerm{

	
	public L1VarTerm(int varIndex, double additional) {
		this(varIndex, 1, additional);
	}
	
	public L1VarTerm(int varIndex) {
		this(varIndex, 1, 0);
	}
	
	public L1VarTerm(int varIndex, double multiplier, double additional) {
		super(varIndex, multiplier, additional);
	}

	
	
//	FIXME: use the static function in this method
//	ASSUMPTION |m x + a|
	@Override
	public Map<String, Integer> addObjectiveTermToProblem(LPProblem problem) {
		
		Map<String, Integer> vars = new HashMap<String, Integer>();
		LPObjectiveFunction objective = problem.getObjectiveFunction();
		
//		System.out.println(objective);
		int numVars = problem.getNumberVariables();
		
		String name = "l1norm_"+varIndex;
		
		LPVariable normFoVar = new LPVariable(name, -Double.MAX_VALUE, Double.MAX_VALUE);
		LPVariable normFoVarPos = new LPVariable(name+"_pos", 0, Integer.MAX_VALUE);
		LPVariable normFoVarNeg = new LPVariable(name+"_neg", 0, Integer.MAX_VALUE);
		vars.put(name, numVars);
		vars.put(name+"_pos", numVars+1);
		vars.put(name+"_neg", numVars+2);
		
		problem.addVariable(normFoVar);
		problem.addVariable(normFoVarPos);
		problem.addVariable(normFoVarNeg);
		
		try {
			LPProblemRow rowFo = new LPProblemRow();
			rowFo.addTerm(varIndex, -multiplier);
			rowFo.addTerm(numVars, 1);
			
			
			LPConstraint constFo = new LPConstraint(LPConstraintType.EQUALITY, rowFo, additional);
			problem.addConstraint(constFo);
			
			LPProblemRow rowFoPosNeg = new LPProblemRow();
			rowFoPosNeg.addTerm(numVars+1, -1);
			rowFoPosNeg.addTerm(numVars+2, 1);
			rowFoPosNeg.addTerm(numVars, 1);
			
			LPConstraint constPosNeg = new LPConstraint(LPConstraintType.EQUALITY, rowFoPosNeg, 0.0);
			problem.addConstraint(constPosNeg);
			objective.addRow(numVars+1, 1);
			objective.addRow(numVars+2, 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vars;
	}
	
	static public Map<String, Integer> splitNegAndPosVariable(LPProblem problem, int idx, String posVarName, String negVarName, Double lower, Double upper) throws WrongFormulationException{
		Map<String, Integer> variablePositions = new HashMap<String, Integer>();
		
		double lowerLimitNegative = 0;
		double upperLimitNegative = Double.MAX_VALUE;
		
		double lowerLimitPossitive = 0;
		double upperLimitPossitive = Double.MAX_VALUE;
		
		if(lower!=null){
			upperLimitNegative = (lower<0)?Math.abs(lower):0.0;
			lowerLimitPossitive = (lower>0)?lower:0.0;
		}
		if(upper!=null){
			lowerLimitNegative = (upper<0)?Math.abs(upper):0.0;
			upperLimitPossitive = (upper>0)?upper:0.0;
			
		}

//		System.out.println(idx + "\t[l]" + lower + "\t[u]" + upper+ "\t[ln]" + lowerLimitNegative + "\t[un]" + upperLimitNegative + "\t[lp]" + lowerLimitPossitive+ "\t[up]" + upperLimitPossitive );
		int numVariables = problem.getNumberVariables();
		LPVariable normFoVarNeg = new LPVariable(negVarName, lowerLimitNegative, upperLimitNegative);
		LPVariable normFoVarPos = new LPVariable(posVarName, lowerLimitPossitive, upperLimitPossitive);
		
		int varPos = numVariables;
		int varNeg = numVariables+1;
		problem.addVariable(normFoVarPos);
		variablePositions.put(posVarName, varPos);
		problem.addVariable(normFoVarNeg);
		variablePositions.put(negVarName, varNeg);
		
		LPProblemRow rowFoPosNeg = new LPProblemRow();
		try {
			rowFoPosNeg.addTerm(varPos, -1);
			rowFoPosNeg.addTerm(varNeg, 1);
			rowFoPosNeg.addTerm(idx, 1);
			
		} catch (LinearProgrammingTermAlreadyPresentException e) {
			e.printStackTrace();
			throw new WrongFormulationException("Linear Programming Term Already Present");
		}
		
		
		LPConstraint constPosNeg = new LPConstraint(LPConstraintType.EQUALITY, rowFoPosNeg, 0.0);
		problem.addConstraint(constPosNeg);
		
		return variablePositions;
	}
	

//	@Override
//	public void addObjectiveTermToProblem(AbstractSSBasicSimulation<?> problem)
//			throws Exception {
//		
//		LPProblem lp = problem.getProblem();
//		addObjectiveTermToProblem(lp);
//		
//		int numVars = lp.getNumberVariables();
//		
//		
//		problem.putVarMappings(lp.getVariable(--numVars).getVariableName(), numVars);
//		System.out.println(numVars);
//		problem.putVarMappings(lp.getVariable(--numVars).getVariableName(), numVars);
//		problem.putVarMappings(lp.getVariable(--numVars).getVariableName(), numVars);
//		
//	}
	
}
