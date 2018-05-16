To install this archetype on your machine:
1. Check out the source code from git
2. Open a command line window and navigate to the project root folder (the one containing pom.xml)
3. Run the "mvn clean install" command
4. Wait for the "Build succeeded" message
5. You are now ready to start using the archetype

To use this archetype to create a new project (requires the archetype to be installed):
1. You can use the "mvn archetype:generate" command to create a new project using this archetype
2. However, it is recommended to use Eclipse for this purpose rather than the command line
3. Within Eclipse, navigate to the menu option: File -> New -> Other -> Maven -> Maven Project
4. Specify the project location (the default workspace location should usually work)
5. Ensure that the "Create a simple project (skip archetype selection)" checkbox is unchecked
6. Proceed to the next screen, titled "Select an archetype"
7. Look for the API test framework archetype within the list of archetypes displayed
8. Select the archetype and proceed to the next screen
9. Specify an appropriate Group Id, Artifact Id and Version for your project, and click Finish
10. You should now see your new project in Eclipse, created based on this archetype