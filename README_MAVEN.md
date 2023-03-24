## What the hell is Maven?

Maven is a build automation tool often used to automate building and maintaing Java projects.

## Why do we need it?

Technically, we don't. We could survive without it. But! It allows us to easily include plugins and dependencies that do all sorts of cool things for us.

## The pom.xml file

The pom.xml file is the most important file in the Maven family. It's where we tell Maven what we want it to do, include, how to build our project, etc. Take a look at it, it has some plugins, some dependencies, and some other cool details.

## JavaFX

We're using Maven because it's the easiest way to include JavaFX into the project, without having to configure all of that annoying stuff like classpaths. Maven allows us to automate this, so that our program will run hassle-free, just by clicking "Go". When you clone the repository, Maven should automatically download all of the dependencies for you, and you should be good to go.

## Help! Something went wrong with Maven!

Okay, that's fine, don't panic! I'd recommend you delete the local version of the repository you had, and start anew with a fresh clone. I had to add Maven to an existing project, which I've never done before, so I may have gotten some things wrong.

It works for me on my machine, but maybe it doesn't for you. If you've tried starting with a fresh clone and that didn't work, try the following:

### Refreshing your maven project

Once you've opened the project in IntelliJ, you should see this little tab on the right side:

<a href="https://www.jetbrains.com/help/idea/maven-projects-tool-window.html">Maven Tool Window</a>

If you can't see that window, or you can't get it to appear, try this instead:

<a href="https://www.jetbrains.com/help/idea/convert-a-regular-project-into-a-maven-project.html#develop_with_maven">How to add Maven support to an existing project</a>

## The project lifecycle

In the <a href="https://www.jetbrains.com/help/idea/maven-projects-tool-window.html">Maven Tool Window</a>, there should be what look like folders. One of them will be called "Lifecycle". This is the important one, don't worry about the others.

This neat little folder has some plugins in there that will automate some things for us. Here's what they do:

- Clean : Cleans the directory. Any generated files (like /target/ ) will be removed.
- Validate : Attempts to validate your code before compiling it, to catch errors even quicker than waiting for a successful compile.
- Compile : Compiles the code and generated any bits and bobs that it feels like.
- Test : Runs all the tests that are found in the /src/test/ directory.
- Package : This will wrap up all of our code, along with all of it's dependencies (JavaFX) into a neat little uber Jar file, that you can launch super easily on your command line using `java -jar MyNewProject.jar`
- Verify : Performs additional checks and validations on the built package, such as running integration tests