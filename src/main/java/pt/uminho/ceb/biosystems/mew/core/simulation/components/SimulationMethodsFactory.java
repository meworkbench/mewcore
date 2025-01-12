package pt.uminho.ceb.biosystems.mew.core.simulation.components;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.solvers.lp.LPProblem;

public class SimulationMethodsFactory extends AbstractSimulationMethodsFactory{

	protected Map<String, Class<? extends LPProblem>> mapTypeMethods;

	public SimulationMethodsFactory(Map<String,Class<?>> mapMethods)
	{
		super(mapMethods);
		this.mapTypeMethods = new HashMap<String, Class<? extends LPProblem>>();
		for(String id : mapMethods.keySet())
			mapTypeMethods.put(id, getProblemClass(mapMethods.get(id)));
	}
	
	@Override
	public void registerMethod(String id, Class<?> method){
		super.registerMethod(id, method);
		mapTypeMethods.put(id, getProblemClass(method));
	}
	
	public Class<? extends LPProblem> getProblemTypeFromMethod(String method){
		return mapTypeMethods.get(method);
	}
	
//	public void addProperty(String key, Object value){
//		methodProperties.put(key, value);
//	}
//	
//	public void removeProperty(String key){
//		
//		methodProperties.remove(key);
//	}
//	
//	public Object getProperty(String key){
//		return methodProperties.get(key);
//	}
	
	
	@SuppressWarnings({"unchecked"})
	private static <T extends LPProblem> Class<T> getProblemClass(final Class<?> klass){
		
		Class<T> ret = null;
		
		try{
			ParameterizedType parameterizedType =
				       (ParameterizedType) klass.getGenericSuperclass();
			ret = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		}catch (Exception e) {
			// When the COBRA simulation methods are registered in the CC an exception is thrown
			// java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
			// The reason must be found in the future
//			e.printStackTrace();
		}
		
		return ret;
	}

}
