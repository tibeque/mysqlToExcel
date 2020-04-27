package entity;


public class Excel {
	private String ip;
	private String dbName;
	private String tableName;
	private String dataSource;
	private String total;
	private String createTime;
	private String lastUpdateTiem;
	
	public Excel() {
		this.ip = "";
		this.dbName = "";
		this.tableName = "";
		this.dataSource = "";
		this.total = "";
		this.createTime = "";
		this.lastUpdateTiem = "";
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTiem() {
		return lastUpdateTiem;
	}
	public void setLastUpdateTiem(String lastUpdateTiem) {
		this.lastUpdateTiem = lastUpdateTiem;
	}
	
	@Override
	public String toString() {
		return "Excel [ip=" + ip + ", dbName=" + dbName + ", tableName=" + tableName + ", dataSource=" + dataSource
				+ ", total=" + total + ", createTime=" + createTime + ", lastUpdateTiem=" + lastUpdateTiem + "]";
	}
	
}
