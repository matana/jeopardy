# jeopardy

#####Description

[jeopardy](https://github.com/matana/jeopardy) is a RESTful classification utility for automatic validation of free text answers in learning systems. It is build on the [WEKA](http://www.cs.waikato.ac.nz/ml/weka/) NaiveBayes algorithm implementation. The application also uses the [Tartarus Snowball](http://snowball.tartarus.org/) stemming algorithm for feature set reduction.

#####Prerequisites

* [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](http://maven.apache.org/download.cgi)

#####Building and starting the application via Terminal

To build and run the application you need to excecute the following commands within the root directory of the project...

* Build the service: ```mvn clean install -Dmaven.test.skip=true``` 
* Run the service: ```java src/main/java/de/uni_koeln/info/Application```

#####REST-Interface

The MVC controller containing the two methods bellow is defined in   [de.uni_koeln.info.controller.RESTfulController](https://github.com/matana/jeopardy/blob/master/src/main/java/de/uni_koeln/info/controller/RESTfulController.java)

GET `http://localhost:8080/validate?questionId=<QUESTION_ID>&text=<ANSWER>`

POST `http://localhost:8080/train`    
The training routin of the POST method is build on the following [scheme](https://github.com/matana/jeopardy/blob/master/freetext.json). Here a short excerpt:    

```
"question": {
            "id": 1455,
            "text": "Erläutern Sie, wie es zur Einstellung des chemischen Gleichgewichtes kommt.",
            ...
        },
        "card_data": {
            ...,
            "answer": { // THIS IS THE TOP ANSWER
                ...,
                "text": "Gemäß dem Massenwirkungsgesetz und in Abhängigkeit von der Temperatur ...",
                ...
            },
            "scored_answers": [ // ARRAY OF SCORED ANSWERS, SCORED FROM 1 -3
                {
                    ...,
                    "text": "Im chemischen Gleichgewicht ist die Geschwindigkeit von Hin- und Rückreaktion ...",
                    "score": 3
                },
                { 
                  ...
                }
            ]
```

#####UML

![alt tag](https://raw.githubusercontent.com/matana/jeopardy/master/classDiagram.png)
