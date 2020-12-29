Convert Hotwire demo (https://hotwire.dev) to SpringBoot

Status : done through 6:40 of the demo video

Next Up: Web Sockets

PRs welcome.

-----
To run:
   
    $ mvn package
    $ java -jar target/hotwire-demo-chat-1.jar

The chat server creates an H2 database
chat-db.h2.mv.db (if it doesn't exist)
in the current working directory
and starts listening for HTTP requests
on http://localhost:8080/rooms.

-------
## Notes on Turbo Streams

From @4:40 in demo video:
> Turbo streams deliver:
>   * page changes
>   * over 
>     * web sockets or 
>     * in response to form submissions
>   * using
>     * just html and
>     * a set of crud-like action tags
> 
> The action tags let you:
>   * append,
>   * prepend to,
>   * replace, and
>   * remove
>   * any DOM element from the existing page, and
>   * are strictly limited to DOM changes (no JS).
> 
> If you can't do what you want with just DOM changes,
> then "connect a Stimulus controller."
> 

As of 4:40,
while you can 
add a new message 
to the chat room 
from a "framed" form
on the room detail page,
the new message is displayed
by a refresh of the room detail page.

As the number of messages in a room increases,
at some point posting a new message will
have a noticeable lag.

With a stream, 
adding a new message will
append to a DOM element,
which will not appear slower as
the message list size grows.


### Note: turbo-streams != web sockets

Two different things.  Turbo streams work fine with a standard
HTTP POST and response.
