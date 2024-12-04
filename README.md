# PostJava: Basic PostScript Interpreter
Aaron Howe
11635434

This is a console application written in the Java programming language, implementing a PostScript interpreter with its most common and basic operations, categorized as:
- Stack
- Arithmetic
- Dictionary
- String
- Boolean
- Flow Control
- Input/Output
## Instructions for Program Set-Up and Execution
1. Install the Java Runtime Environment
    - Since this program is entirely written in Java, you'll need to have a version of JRE downloaded and installed to your machine if you don't already. You can find the necessary JRE at this [link](https://www.java.com/en/download/manual.jsp).
2. Configure the Local Bash/Batch Files for Program Execution (optional)
    - There are four different scripts at the root of the project directory, the configuration of these scripts provides the best user experience for this interpreter, but is not necessary.
    - Files ending in `.bat` are for users of the Windows operating system, while files ending in `.sh` are for users of Unix-like operating systems, such as Linux distributions or Macintosh.
    - Windows Configuration Guide:
        1. Via Windows File Explorer, navigate to the location in which you've chosen to store the project folder.
        2. Once there, you should see a file named `install_pj.bat`, right-click this file and then click 'Run as Administrator'. If you have a terminal window currently open, you will be prompted to restart it after this step.
        3. Now open the Windows Search Bar, search for your favorite command-line interface (Powershell, Git Bash, CMD, etc.), and type and enter `pj' to invoke the interpreter.
        NOTE: I did not officially test this batch script, as I am running this program via Linux, I apologize for my laziness. Should it not work, Jump over to the traditional method.
    - Unix/Linux Configuration Guide:
        1. Via your system terminal, navigate to the project's root directory via `cd path/to/project`.
        2. Type and enter the following to ensure the bash scripts are executable:
            `chmod +x ps install_pj.sh`
        3. Then, to configure the installation script, type and enter `sudo ./install_pj.sh`, and enter your password when prompted.
        4. You can now enter `pj` from any directory in your terminal to invoke the interpretor.
3. If You Prefer the Traditional Method of Compiling and Running
    - Unix/Linux:
        1. Compile: `javac -cp "lib/*" test/*.java src/*.java main/*.java`
        2. Run: `java -cp "lib/*:main:src:test" Main`
    - Windows:
        1. Compile: `javac -cp "/lib/*" test/*.java src/*.java main/*.java`
        2. RunL `java -cp "lib/*;main;src;test" Main`
## Lexical & Dynamic Scoping
By default the interpreter uses dynamic scoping, and you as the user are made aware of what scope the interpreter is using via the prompt `[PJ:D > 0]`, where the character 'D' designates the interpreter is using dynamic scoping. By entering the command `lex`, the interpreter will switch over to lexical scope, and to switch back, simply enter the command `dyn`.
