package me.Bokum.BlockChange;

public class ChangingBlock {

	public int replaceId;
	public byte replaceData;
	public int changeId;
	public byte changeData;
	
	public ChangingBlock(int rid, byte rdata, int cid, byte cdata){
		this.replaceId = rid;
		this.replaceData = rdata;
		this.changeId = cid;
		this.changeData = cdata;
	}
}
