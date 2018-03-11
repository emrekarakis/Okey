package okey;

public class Tiles implements Comparable<Tiles> {

	private String color;
	private Integer realNumber;
	private Integer code;
	private Boolean falseJoker = Boolean.FALSE;
	private Boolean joker = Boolean.FALSE;

	public Tiles() {

	}

	public Tiles(String color, Integer realNumber) {
		this.color = color;
		this.realNumber = realNumber;
	}

	public Tiles(String color, Integer realNumber, Integer code) {
		this.color = color;
		this.realNumber = realNumber;
		this.code = code;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(Integer realNumber) {
		this.realNumber = realNumber;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getFalseJoker() {
		return falseJoker;
	}

	public void setFalseJoker(Boolean falseJoker) {
		this.falseJoker = falseJoker;
	}

	public Boolean getJoker() {
		return joker;
	}

	public void setJoker(Boolean joker) {
		this.joker = joker;
	}

	@Override
	public int compareTo(Tiles tile) {
		int code = ((Tiles) tile).getCode();
		/* For Ascending order */
		return this.code - code;
	}

}
