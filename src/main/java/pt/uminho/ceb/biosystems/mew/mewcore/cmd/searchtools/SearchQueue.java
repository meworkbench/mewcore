package pt.uminho.ceb.biosystems.mew.mewcore.cmd.searchtools;

public enum SearchQueue {
	
	BIO {
		@Override
		public String getFlags() {
			return "-q bio";
		}
	},
	BIOCNAT {
		@Override
		public String getFlags() {
			return "-q biocnat";
		}
	},
	ANY {
		@Override
		public String getFlags() {
			return "-q default";
		}
	};
	
	public abstract String getFlags();
}
	
