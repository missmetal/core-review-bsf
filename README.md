## Code Review / Refactoring exercise
Please review the following code snippet. Assume that all referenced assemblies have been properly included.
The code is used to log different messages throughout an application. We want the ability to be able to log to a text file, the console and/or the database. Messages can be marked as message, warning or error. We also want the ability to selectively be able to choose what gets logged, such as to be able to log only errors or only errors and warnings.
1. If you were to review the following code, what feedback would you give? Please be specific and indicate any errors that would occur as well as other best practices and code refactoring that should be done.
1. Rewrite the code based on the feedback you provided in question 1. Please include unit tests on your code.

## Feedback

There are some lines of code that are not necessary, repetitions and redundances and some variables name are not descriptive.

After that, the desing pattern that were about to be applied aren't clear too, there are no structure. In this case, if it's about a simple console app, it have to have a Main java class and the main static method to run it in console.

* Case 1:


    ` Connection connection = null; /* Is not necessary to initialize 'connection' to null when the field is declared in the same method */` 

    It should be: 

    `Connection connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + ` `"://" + dbParams.get("serverName")`
    `+ ":" + dbParams.get("portNumber") + "/", connectionProps); `


* Case 2:

    `int t = 0; /* What are 't' value representing? */`

* Case 3: 

    `if (error && logError) {
                t = 2;
            }  /* Curly braces are redundant */`


* Case 4: 

    `if (messageText == null || messageText.length() == 0) {
    return;
    }`

    `/*Is redundant, message is never null or empty*/`

* Case 5: All variables into a method should be created at the beggining of it

* Case 6:

    `private boolean initialized; /*Is never used*/`

* Case 7: Unless in classes or methods that are unique and global for all the app or some singletons, it's not recommended to use static methods.

* Case 8: For logging messages, is not recomended to use strings, only instancing the logger value is enough.

    Don't use: `String loggedMessage;` 

    Is recommended: `logger.info(message);`