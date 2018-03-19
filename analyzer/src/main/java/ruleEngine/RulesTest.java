package ruleEngine;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class RulesTest {

	@Condition
	public boolean itRains(@Fact("rain") boolean rain) {
		return rain;
	}

	@Action
	public void takeAnUmbrella() {
		System.out.println("It rains, take an umbrella!");
	}

}
