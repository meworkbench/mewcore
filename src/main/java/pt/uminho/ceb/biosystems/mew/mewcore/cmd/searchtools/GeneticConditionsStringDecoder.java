package pt.uminho.ceb.biosystems.mew.mewcore.cmd.searchtools;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.decoder.ISolutionDecoder;

import pt.uminho.ceb.biosystems.mew.mewcore.optimization.decoder.ISteadyStateDecoder;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.components.GeneticConditions;

public class GeneticConditionsStringDecoder implements ISolutionDecoder<IRepresentation, String> {
	
	private static final long		serialVersionUID	= 1L;
	
	protected ISteadyStateDecoder	_decoder			= null;
	
	public GeneticConditionsStringDecoder(ISteadyStateDecoder decoder) {
		_decoder = decoder;
	}
	
	public Object deepCopy() throws Exception {
		return null;
	}
	
	@Override
	public String decode(IRepresentation representation) throws Exception {
		GeneticConditions gc = _decoder.decode(representation);
		String decoded = null;
		if (!gc.isOverUnder())
			decoded = gc.toStringOptions(" ", true);
		else
			decoded = gc.toStringOptions(" ", false);
		
		return decoded;
	}
	
}
