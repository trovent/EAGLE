
/*
    Copyright (C) 2017 e-ito Technology Services GmbH
    e-mail: info@e-ito.de
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package hr.eito.e2nwkite.business.manager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hr.eito.e2nwkite.aql.model.AqlParams;
import hr.eito.e2nwkite.aql.model.dto.RulesetReturnResult;
import hr.eito.e2nwkite.utils.CustomError;
import hr.eito.e2nwkite.utils.CustomMessage;
import hr.eito.model.JsonReturnData;

/**
 * Tests the AQLManagerImpl.
 *
 * @author Hrvoje
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/app-config.xml" })
@ActiveProfiles("test")
public class AQLManagerImplTest {
	
	@Autowired
	private AQLManager aqlManager;

	/**
	 * Runs before the tests start.
	 */
	@BeforeClass
	public static void testStart() {}
	
	/**
	 * Runs after the tests end.
	 */
	@AfterClass
	public static void testEnd() {}

	/**
	 * Check we have a manager.
	 */
	@Test
	public void testAqlManager() {
		Assert.assertNotNull(aqlManager);
	}
	
	/**
	 * Test getting aql rules list
	 */
	@Test
	public void testAqlRulesList() {
		JsonReturnData<RulesetReturnResult> returnResult = aqlManager.aqlRulesList();
		
		Assert.assertNotNull("List of rules null", 
				returnResult.getContent());
		Assert.assertEquals("List of filtered rules not expected size", 
				3, returnResult.getContent().getRecordsFiltered());
		Assert.assertEquals("List of rules not expected size", 
				3, returnResult.getContent().getRecordsTotal());
	}
	
	/**
	 * Test adding AQL rule
	 */
	@Test
	public void testAddAqlRule() {
		// Test adding empty rule
		AqlParams paramsEmptyRule = new AqlParams();
		paramsEmptyRule.setRule("");
		JsonReturnData<RulesetReturnResult> resultEmptyRule = aqlManager.addAqlRule(paramsEmptyRule);
		
		Assert.assertFalse("Empty rule did not return error status", 
				resultEmptyRule.isOK());
		Assert.assertEquals("Empty rule did not return expected error message", 
				CustomError.AQL_RULE_MISSING.getErrorMessage(), resultEmptyRule.getErrorMessage());
		
		// Test adding existing rule
		AqlParams paramsExistingRule = new AqlParams();
		paramsExistingRule.setRule(aqlManager.aqlRulesList().getContent().getData().get(0).getRule()); // Rule from first ruleset
		JsonReturnData<RulesetReturnResult> resultExistingRule = aqlManager.addAqlRule(paramsExistingRule);
		
		Assert.assertFalse("Adding existing rule did not return error status", 
				resultExistingRule.isOK());
		Assert.assertEquals("Adding existing rule did not return expected error message", 
				CustomError.AQL_RULE_ALREADY_EXISTS.getErrorMessage(), resultExistingRule.getErrorMessage());
		
		// Test addition of invalid rule
		String invalidRule = "SOME INVALID RULE";
		AqlParams paramsInvalidRule = new AqlParams();
		paramsInvalidRule.setRule(invalidRule);
		JsonReturnData<RulesetReturnResult> resultInvalidRule = aqlManager.addAqlRule(paramsInvalidRule);
		
		Assert.assertFalse("Adding invalid rule did not return error status", 
				resultInvalidRule.isOK());
		
		// Test correct insert
		String newRule = "SRC MATCHES DST";
		String newDescription = "Description for new rule";
		AqlParams paramsCorrectRule = new AqlParams();
		paramsCorrectRule.setRule(newRule);
		paramsCorrectRule.setDescription(newDescription);
		JsonReturnData<RulesetReturnResult> resultCorrectRule = aqlManager.addAqlRule(paramsCorrectRule);
		
		Assert.assertNotNull("Adding rule action returned null object", 
				resultCorrectRule.getContent());
		Assert.assertEquals("Adding rule action did not return exactly 1 filtered record", 
				1, resultCorrectRule.getContent().getRecordsFiltered());
		Assert.assertEquals("Adding rule action did not return exactly 1 record", 
				1, resultCorrectRule.getContent().getRecordsTotal());
		Assert.assertEquals("Adding rule action did not return expected ID", 
				new Integer(4), resultCorrectRule.getContent().getData().get(0).getId());
		Assert.assertEquals("Adding rule action did not return expected rule text", 
				newRule, resultCorrectRule.getContent().getData().get(0).getRule());
		Assert.assertEquals("Adding rule action did not return expected rule description", 
				newDescription, resultCorrectRule.getContent().getData().get(0).getDescription());
	}
	
	/**
	 * Test editing AQL rule
	 */
	@Test
	public void testEditAqlRule() {
		// Test editing with empty rule
		AqlParams paramsEmptyRule = new AqlParams();
		paramsEmptyRule.setRule("");
		JsonReturnData<RulesetReturnResult> resultEmptyRule = aqlManager.editAqlRule(paramsEmptyRule);
		
		Assert.assertFalse("Empty rule edit did not return error status", 
				resultEmptyRule.isOK());
		Assert.assertEquals("Empty rule edit did not return expected error message", 
				CustomError.AQL_RULE_MISSING.getErrorMessage(), resultEmptyRule.getErrorMessage());
		
		// Test editing with existing rule
		AqlParams paramsExistingRule = new AqlParams();
		paramsExistingRule.setRule(aqlManager.aqlRulesList().getContent().getData().get(0).getRule()); // Rule from first ruleset
		JsonReturnData<RulesetReturnResult> resultExistingRule = aqlManager.editAqlRule(paramsExistingRule);
		
		Assert.assertFalse("Editing to existing rule did not return error status", 
				resultExistingRule.isOK());
		Assert.assertEquals("Editing to existing rule did not return expected error message", 
				CustomError.AQL_RULE_ALREADY_EXISTS.getErrorMessage(), resultExistingRule.getErrorMessage());
		
		// Test editing missing rule
		AqlParams paramsMissingRule = new AqlParams();
		paramsMissingRule.setId(99);
		paramsMissingRule.setRule("SRC = \"0.0.0.0\"");
		JsonReturnData<RulesetReturnResult> resultMissingRule = aqlManager.editAqlRule(paramsMissingRule);
		
		Assert.assertFalse("Editing missing rule did not return error status", 
				resultMissingRule.isOK());
		Assert.assertEquals("Editing missing rule did not return expected error message", 
				CustomError.AQL_RULESET_MISSING.getErrorMessage(), resultMissingRule.getErrorMessage());
		
		// Test editing to invalid rule
		String invalidRule = "SOME INVALID RULE";
		AqlParams paramsInvalidRule = new AqlParams();
		paramsInvalidRule.setId(2);
		paramsInvalidRule.setRule(invalidRule);
		JsonReturnData<RulesetReturnResult> resultInvalidRule = aqlManager.editAqlRule(paramsInvalidRule);
		
		Assert.assertFalse("Editing to invalid rule did not return error status", 
				resultInvalidRule.isOK());
		
		// Test correct edit
		Integer id = 2;
		String rule = "PRT > 8000";
		String description = "Edited description for rule 2";
		AqlParams paramsCorrectRule = new AqlParams();
		paramsCorrectRule.setId(id);
		paramsCorrectRule.setRule(rule);
		paramsCorrectRule.setDescription(description);
		JsonReturnData<RulesetReturnResult> resultCorrectRule = aqlManager.editAqlRule(paramsCorrectRule);
		
		Assert.assertNotNull("Edit rule action returned null object", 
				resultCorrectRule.getContent());
		Assert.assertEquals("Edit rule action did not return exactly 1 filtered record",
				1, resultCorrectRule.getContent().getRecordsFiltered());
		Assert.assertEquals("Edit rule action did not return exactly 1 record", 
				1, resultCorrectRule.getContent().getRecordsTotal());
		Assert.assertEquals("Edit rule action did not return edited record with expected ID", 
				id, resultCorrectRule.getContent().getData().get(0).getId());
		Assert.assertEquals("Edit rule action did not return edited record with expected rule text", 
				rule, resultCorrectRule.getContent().getData().get(0).getRule());
		Assert.assertEquals("Edit rule action did not return edited record with expected rule description", 
				description, resultCorrectRule.getContent().getData().get(0).getDescription());
	}
	
	/**
	 * Test deleting AQL rule
	 */
	@Test
	public void testDeleteAqlRule() {
		// Test deleting missing rule
		AqlParams paramsMissingRule = new AqlParams();
		paramsMissingRule.setId(99);
		JsonReturnData<String> resultMissingRule = aqlManager.deleteAqlRule(paramsMissingRule);
		
		Assert.assertFalse("Missing rule deletion did not return error status", 
				resultMissingRule.isOK());
		Assert.assertEquals("Missing rule deletion did not return expected error message", 
				CustomError.AQL_RULESET_MISSING.getErrorMessage(), resultMissingRule.getErrorMessage());

		// Test correct delete
		Integer id = 2;
		AqlParams paramsCorrectRule = new AqlParams();
		paramsCorrectRule.setId(id);
		JsonReturnData<String> resultCorrectRule = aqlManager.deleteAqlRule(paramsCorrectRule);
		
		Assert.assertTrue("Delete rule action did not return OK result", 
				resultCorrectRule.isOK());
	}
	
	/**
	 * Test AQL rule validation
	 */
	@Test
	public void testAqlRuleValidation() {
		String[] ruleList = {
				"SRC MATCHES DST"
				, "(SRC FOUND (LISTED \"now-10m\", \"now\", \"some_identifier\" AS \"some-identifier18\"))"
				, "(SUM \"now-15m\", \"now-5m\" ,\"192.168.0.4\") > 10"
				, "(COUNT \"now-60m\", \"now\", \"192.168.0.4/0\" = 0)"
				, "(SUM SSCOPE \"0.0.0.0/0\", \"now-365d\", \"now\", PRT MATCHES 3306) > 0"
				, "(PCARD DSCOPE \"0.0.0.0/0\", \"now-1d\", \"now\", FLAGS MATCHES \"..P...\") > 0"
		};
		for (String rule : ruleList) {
			testSingleAqlRuleValidation(rule, true);
		}
	}
	
	/**
	 * Test AQL rule validation - invalid rule
	 */
	@Test
	public void testAqlRuleValidation_invalid() {
		String[] ruleList = {
				// invalid term
				"invalid_rule",
				 // non-existing keyword SRCA
				"SRCA MATCHES DST"
				 // identifier must start with letter
				, "(SRC FOUND (LISTED \"now-10m\", \"now\", \"some_identifier\" AS \"18some-identifier18\"))"
				// invalid IP address
				, "(SUM \"now-15m\", \"now-5m\", \"392.168.0.4\") > 10"
				// time sign "h" unknown
				, "(COUNT \"now-60h\", \"now\", \"192.168.0.4/0\" = 0)"
				// invalid ip mask
				, "(SUM SSCOPE \"0.0.0.0/33\", \"now-365d\", \"now\", PRT MATCHES 3306) > 0"
				// decimal number invalid
				, "(PCARD DSCOPE \"0.0.0.0/0\", \"now-1d\", \"now\", FLAGS MATCHES \"..P...\") > 0,5"
		};
		for (String rule : ruleList) {
			testSingleAqlRuleValidation(rule, false);
		}
	}
	
	/**
	 * Helper method for validating single aql rule
	 * 
	 * @param rule to be validated
	 * @param isValid expecting valid or invalid rule
	 */
	private void testSingleAqlRuleValidation(final String rule, boolean isValid) {
		AqlParams params = new AqlParams();
		params.setRule(rule);
		JsonReturnData<String> result = aqlManager.aqlRuleValidation(params);
		
		Assert.assertEquals(new StringBuilder().append("Rule:\n")
				.append(rule)
				.append("\nexpected to be " + (isValid?"":"in") + "valid, but got:\n")
				.append(result.getErrorMessage())
				.append("\n")
				.toString()
				, isValid, result.isOK());
		
		if (isValid) {
			Assert.assertEquals(new StringBuilder().append("Expected return message:\n")
					.append(CustomMessage.AQL_RULE_VALID.getMessage())
					.append("\nbut got message:\n")
					.append(result.getContent())
					.append("\n")
					.toString()
					, CustomMessage.AQL_RULE_VALID.getMessage(), result.getContent());
		}
	}
	
}
