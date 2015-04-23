/*
 * Copyright 2010
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of Biological Engineering
 * CCTC - Computer Science and Technology Center
 *
 * University of Minho 
 * 
 * This is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This code is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Public License for more details. 
 * 
 * You should have received a copy of the GNU Public License 
 * along with this code. If not, see http://www.gnu.org/licenses/ 
 * 
 * Created inside the SysBioPseg Research Group (http://sysbio.di.uminho.pt)
 */
package pt.uminho.ceb.biosystems.mew.mewcore.simulation.components;

import java.util.Map;
import java.util.Set;

import pt.uminho.ceb.biosystems.mew.mewcore.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.mewcore.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.formulations.exceptions.MandatoryPropertyException;
import pt.uminho.ceb.biosystems.mew.mewcore.simulation.formulations.exceptions.PropertyCastException;



public interface ISteadyStateSimulationMethod {

	SteadyStateSimulationResult simulate () throws Exception;
	
	ISteadyStateModel getModel ();
	
	public EnvironmentalConditions getEnvironmentalConditions() throws PropertyCastException, MandatoryPropertyException;

	public void setEnvironmentalConditions(EnvironmentalConditions environmentalConditions);

	public GeneticConditions getGeneticConditions() throws PropertyCastException, MandatoryPropertyException;

	public void setGeneticConditions(GeneticConditions geneticConditions); 

	Set<String> getPossibleProperties();
	
	Set<String> getMandatoryProperties();

	void setProperty(String m, Object o);
	
	void putAllProperties(Map<String, Object> properties);
	
	<T> T getProperty (String k);
	
	public Class<?> getFormulationClass();
	
}
