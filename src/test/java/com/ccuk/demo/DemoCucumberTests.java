package com.ccuk.demo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:cucumber/features"}, glue = {"com.ccuk.demo.glue"}, plugin = {"pretty" , "json:build/test-results/test/cucumber.json"})
public class DemoCucumberTests {

}
