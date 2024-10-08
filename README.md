
# Rugiet Web Sample

## How To Run Locally
 
 -- Download and install IntelliJ IDE Community Edition from: https://www.jetbrains.com/idea/download/
 
 -- Download and install Java JDK 19 "Installer" from: [https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html

 -- Open IntelliJ. Select "Get from VCS" button. Select "Github" and log in. (Ensure to download "git" when prompted and "(MAC)download and install x-code tools")

 -- After log in, go to https://github.com/ddoby-app/Rugiet-web-sample/. Click the "Clone" button. 
 -- You will be asked to Log In to Github. Select "Generate Token". On the Generate Token page, leave all selections at default, and generate a token. Copy the generated token. After Generating the token, you will need to Authorize the Token.
 -- After Authorizing the token, Paste the token in IntelliJ. Click Clone and Wait for completion.

 -- In the left panel of IntelliJ, with the "Project" tab selected, click the "Rugiet-web-sample" dropdown. (Win)Right-click(Mac)Ctrl+Click the "pom.xml" file. Select "Maven-> Generate Sources and Update Folders" to add project dependencies.

 -- Create a new directory in hadrian-flow-automation->src->java, create a directory and name it "resources". Inside, create a file and name it "config.properties". Open this file in IntelliJ. Add the following information (and add your credentials as well):

     login.email = 
     login.password = 
     
 -- (MAC ONLY) Open Terminal. Navigate to IdeaProjects/Rugiet-web-sample/Drivers. Enter the following: "xattr -d com.apple.quarantine chromedriver" (this allows chromedriver to bypass Mac security since it was not downloaded from the App Store).    

 -- Open the "TestSuites" directory. (Win)Right-click(Mac)Ctrl+Click the desired Test suite to run. Select "Run..." and watch in excitement!

## Knowledge Base 

 -- Check IntelliJ for debug messages during the run.

 -- If you update Google Chrome to a new version, you will need to download a new chromedriver version. This will get updated periodically, so you may need to keep your Chrome browser up-to-date.

 -- As developers introduce new code, there is a possibility that part so of the workflow may break (due to elements on the page moving or being deleted/renamed). @Domoniq will keep everything updated as much as possible, but please let @Domoniq know if something has broken.

 -- This is currently written in Java, BUT this will change to Python soon.

 -- In order to keep the project up to date, you may need to Pull the updated code from Git. To do so (not in command line), go to the Window menu -> Git -> Pull... and click the Pull button. If you have issues, message @Domoniq.


