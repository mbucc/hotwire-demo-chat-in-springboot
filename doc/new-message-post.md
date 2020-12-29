# New message form POST: HTTP headers

With turbo:

    POST /rooms/1/messages/new HTTP/1.1
    Accept: text/html; turbo-stream, text/html, application/xhtml+xml
    Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryQ1KuQghUtO3JbL1h
    Origin: http://localhost:8080
    Cookie: Idea-df58c19a=1081a546-8835-4eb5-a706-bef371d4e4dc
    Content-Length: 239
    Accept-Language: en-us
    Host: localhost:8080
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.1 Safari/605.1.15
    Referer: http://localhost:8080/rooms/1
    Accept-Encoding: gzip, deflate
    Connection: keep-alive

Without turbo:

    POST /rooms/1/messages/new HTTP/1.1
    Content-Type: application/x-www-form-urlencoded
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
    Accept-Encoding: gzip, deflate
    Accept-Language: en-us
    Host: localhost:8080
    Origin: http://localhost:8080
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.1 Safari/605.1.15
    Connection: keep-alive
    Upgrade-Insecure-Requests: 1
    Referer: http://localhost:8080/rooms/1/messages/new
    Content-Length: 20
    Cookie: Idea-df58c19a=1081a546-8835-4eb5-a706-bef371d4e4dc


Diff:

`+` = Without Turbo

`-` = With Turbo


    -    Accept: text/html; turbo-stream, text/html, application/xhtml+xml
    +    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
         Connection: keep-alive
    -    Content-Length: 239
    +    Content-Length: 20
    -    Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryQ1KuQghUtO3JbL1h
    +    Content-Type: application/x-www-form-urlencoded
         Cookie: Idea-df58c19a=1081a546-8835-4eb5-a706-bef371d4e4dc
         Host: localhost:8080
         Origin: http://localhost:8080
         POST /rooms/1/messages/new HTTP/1.1
    -    Referer: http://localhost:8080/rooms/1
    +    Referer: http://localhost:8080/rooms/1/messages/new
    +    Upgrade-Insecure-Requests: 1
         User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.1 Safari/605.1.15

