package pt.uminho.ceb.biosystems.mew.core.simulation.mfa.ratioconstraints;

public enum RatioConstraintComparator {
	
	EQUAL{
		@Override
		public String toString(){
			return "=";
		}
	},
	
	MINOR{
		@Override
		public String toString(){
			return "<";
		}
	},
	
	MAJOR{
		@Override
		public String toString(){
			return ">";
		}
	},
	
	MINOR_EQUAL{
		@Override
		public String toString(){
			return "<=";
		}
	},
	
	MAJOR_EQUAL{
		@Override
		public String toString(){
			return ">=";
		}
	};
	

	@Override
	public String toString(){
		return this.toString();
	}
}
