
He utilizado mas que nada geminis.

Lo primero que hice fue plantearme que tecnologia usar e ir a la página de spring.io y generar el proyecto. 
Previamente me plantee si era mejor usar una arquitectura de capas o hexagonal, le pregunté a geminis que era 
mas conveniente para este proyecto. 

Luego de crear el dominio, los puertos y adapters principales, me di cuenta de que para hacer la app aún más óptima 
se me ocurrió implementar un mecanismo de caché, asi que averigué que alternativa habia a redis, asi que ahi me 
di cuenta que podía utilizar el propio framework de springboot, no fue nada facil porque queria que en lugar de utilizar
la lista enviada en la solicitud como key, quería utilizar el Id de cada producto individual y solo consultar el archivo json
en caso de ser necesario. Ademas, cuando se usa la anotacion @Cacheable en un metodo, se crea un wrapper en automatico, 
con lo cual tuve que inyectar la clase asi misma para evitar este problema.

Utilice geminis tambien para ayudarme a armar los unit test como mockito, un problema al cual me enfrenté fue que cuando tenia que
hacer el unit test del controller tenia que excluir la capa de seguridad. 

Tambien utilice geminis para generar el Dockerfile y ayudarme a mejorarlo.


