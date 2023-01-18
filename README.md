![Github Release](https://img.shields.io/github/v/release/JoshiCodes/JTA?include_prereleases)

# JTA (Java Telegram API)
###### (Name inspired from the Discord JDA)

This Project is a try in creating a complete Telegram API for Java.
I do not plan to have it work as well as some others, rather as a learning experience for me.

If you need a full Telegram API for Java, have a look at [this](https://github.com/pengrad/java-telegram-bot-api).

## This API is not finished and may not be finished in the future.

## Download
You can download the latest Version as a Jar [here](https://github.com/JoshiCodes/JTA/releases).
You can also import it as a Maven Dependency:
(Replace VERSION with the newest Version)
```xml
<dependency>
    <groupId>de.joshizockt</groupId>
    <artifactId>jta</artifactId>
    <version>VERSION</version>
</dependency>
```

## Usage

To use the API in any way, you need to create a new `JTA` Instance. You do this with the `JTABuilder` class.

```java
    JTABuilder builder = new JTABuilder("YourBotTokenHere");
    JTA jta = builder.build();
```

After that you can use the `JTA` Instance to do more stuff with the API.

Currently, the JTA Class supports the following Methods:
- `JTA#getChat(String id)`
- `JTA#getPrivateChat(long id)`
- `JTA#getSelfUser()`

These Methods return a `RestAction` Object, which you can use to get the Result of the Request.
To get the Result, you need to call the `RestAction#complete()` Method.

```java
    GenericChat chat = jta.getChat("123456789").complete();
```

The `User` and `Chat` Objects returned by the API, can be used to write a Message to the Chat.

```java
    chat.sendMessage("Hello World!").queue();
```

This `sendMessage` Method returns a `RestAction` Object, which you can use to get the `Message` Object of the Message you just sent.
Or you can just call the `#queue()` Method, which will send the Message and not return anything. You can also provide a Consumer in this queue Method to execute after sending or to handle an error.

```java
    // Get the Message Object of the Message you just sent
    Message message = chat.sendMessage("Hello World!").complete();

    // Send the Message and execute the Consumer after sending
    chat.sendMessage("Hello World!").queue(message -> {
        // Do something with the Message Object
    });
    
    // Use two Consumers to handle the Message and the Error
    chat.sendMessage("Hello World!").queue(message -> {
        // Do something with the Message Object
    }, error -> {
        // Handle the Error
    });
    
```

If you really want, you can call Requests Instances directly with the `JTA` Instance.
This is done automatically by the `RestAction` Objects, but you can do it yourself if you want.

```java
    
    // Create a new GetSelfRequest Instance
    JTA jta = [...]; // Get the JTA Instance
    GetSelfRequest request = new GetSelfRequest(jta);
    
    // Execute the Request
    User self = jta.getRequestHandler().execute(request);
    
    
    
    // This does the same as the above
    User self = jta.getSelfUser().complete();    

```