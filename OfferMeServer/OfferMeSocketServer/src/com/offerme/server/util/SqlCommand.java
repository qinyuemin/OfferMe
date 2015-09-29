package com.offerme.server.util;

public class SqlCommand {
	
	private String select = "";
	private String from = "";
	private String where = "";
	private int start = -1;
	private int size = -1;
	private String order = "";
	
	public SqlCommand(String select,String from, String where,int start,int size,String order)
	{
		this.select = select;
		this.from = from;
		this.where = where;
		this.start = start;
		this.size = size;
		this.order = order;		
	}
	
	public String getSql() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		if("".equals(select))
		{
			throw new Exception("Have no select ");
		}
		if("".equals(from))
		{
			throw new Exception("Have no from ");
		}
		sb.append("select ");
		sb.append(select);
		sb.append(" from ");
		sb.append(from);
		if(!"".equals(where))
		{
			sb.append(" where ");
			sb.append(where);
		}
		if(!"".equals(order))
		{
			sb.append(" order by ");
			sb.append(order);
		}
		if (start == 0 && size <= 0)
		{
			sb.append(" limit ");
			sb.append(0);
		}
		else if (start >= 0 && size > 0) 
		{
			sb.append(" limit ");
			sb.append(start);
			sb.append(',');
			sb.append(size);
		}
		return sb.toString();
	}
}
