package pt.uminho.ceb.biosystems.mew.mewcore.simplification.nullspace.structure.interfaces;

public interface IReducedModelLong extends IReducedModel{

	public long[][] getStoichValues();
	public long[][] getExternalStoichValues();
	
}
