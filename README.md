# A simple hystrix example

If BOTH applications are started. 
http://localhost:8080/to-read returns a list of three books (given 
by the remote bookstore)

If only the reading application is started. The call can't reach 
the bookstore. So, a fallback is returned.

Made by [Alexander Lüdeke](http://apage4u.de) 

