TestData File Location - /UITrello/testData/testUi.xlsx
Columns in Test File - 1. Username
					   2. Password
					   3. WorkSpace Name(needs to be present in the login account)
					   4. Board Name(needs to be present in the login account)
					   5. Card Name

Project Type - Maven Project using Selenium					   
Command to Run Test in UI mode from command line -> mvn clean test -P UI -U -Dmaven.test.failure.ignore=true
Command to Run Test in Headless mode from Command Line -> mvn clean test -P HeadlessTest -U -Dmaven.test.failure.ignore=true123
