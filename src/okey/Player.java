package okey;

import java.util.List;
import java.util.Map;

public class Player {

	private String name;
	private Integer number;
	private List<Tiles> stack;
	private Integer perCount;
	private Integer countOfResidual;
	private List<Tiles> residual;
	private Boolean hasJoker;
	private Map<Integer, List<Tiles>> possiblePerForDifferentColors;

	public Player() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Tiles> getStack() {
		return stack;
	}

	public void setStack(List<Tiles> stack) {
		this.stack = stack;
	}

	public Integer getPerCount() {
		return perCount;
	}

	public void setPerCount(Integer perCount) {
		this.perCount = perCount;
	}

	public Integer getCountOfResidual() {
		return countOfResidual;
	}

	public void setCountOfResidual(Integer countOfResidual) {
		this.countOfResidual = countOfResidual;
	}

	public List<Tiles> getResidual() {
		return residual;
	}

	public void setResidual(List<Tiles> residual) {
		this.residual = residual;
	}

	public Boolean getHasJoker() {
		return hasJoker;
	}

	public void setHasJoker(Boolean hasJoker) {
		this.hasJoker = hasJoker;
	}

	public Map<Integer, List<Tiles>> getPossiblePerForDifferentColors() {
		return possiblePerForDifferentColors;
	}

	public void setPossiblePerForDifferentColors(Map<Integer, List<Tiles>> possiblePerForDifferentColors) {
		this.possiblePerForDifferentColors = possiblePerForDifferentColors;
	}

}
