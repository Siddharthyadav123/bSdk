# README #

Android SDK.

* Version 0.0.1

# Contribution guidelines #

## Coding conventions ##

Please follow [this coding conventions](https://drive.google.com/a/myantra.com/file/d/0BwQtOzgM0HcOSkZTTjJWRkxtQ0U/view?usp=sharing) to contributing the code to this repository.

## Commit format ###

### Message structure ###

* A commit messages consists of three distinct parts separated by a blank line: the **title**, an optional **body** and an optional **footer**.

**The layout looks like this :**


```
#!java

type : subject

body

Footer
```


**The Title** consists of the type of the message and subject. The title and can be one of these types:

* feat : a new feature
* bug : a bug fix
* docs : changes to documentation
* style : formatting, missing semi colons, etc; no code change
* refactor : refactoring production code
* test : adding tests, refactoring test; no production code change
* chore : updating build tasks, package manager configs, etc; no production code change

The Subjects should be no greater than 50 characters
Use an imperative tone to describe what a commit does, rather than what it did.

When writing a body, the blank line between the title and the body is required and you should limit the length of each line to no more than 72 characters.

* bullets can be used in body to make it concise and clear


```
#!java

Footer (optional) can have references like
Resolves: #123,
See also: #456, #789
```