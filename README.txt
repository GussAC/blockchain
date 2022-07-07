###########################################################################

						API	BLOCK CHAIN

###########################################################################

NOTAS GENERALES

CONFIGURADO EN EL PUERTO 8081
AGREGAR AL ARCHIVO application.properties LA RUTA DONDE SE ENCUENTRA EL
ARCHIVO DE REGISTRO CON EL IDENTIFICADOR "pathfile", NO ES NECESARIO
CREAR EL ARCHIVO CSV, LA APLICACION SE ENCARGA DE CREARLO, SOLO HAY QUE
INDICAR LA RUTA

EJEMPLO: pathfile=C:/tmp/blockchaindata.csv
___________________________________________________________________________
###########################################################################
IMPORTANTE: 

EL ARCHIVO CSV NO SE BLOQUEA CON PETICIONES SIMULTANEAS, USA
UN METODO SINCRONIZADO Y SE ACCEDE A ESTE A TRAVES DE HILOS.

SE PUEDEN GENERAR VARIOS BLOQUES Y DESPUES ABRIR EL ARCHIVO
CON EXCEL PARA SIMULAR USO SIMULTANEO , UNA VEZ QUE  CIERRE 
EL ARCHIVO, LAS PETICIONES HECHAS DURANTE ESE TIEMPO SE VERAN
REFLEJADAS.
 
EL ARCHIVO CSV SE LIMPIA CADA VEZ QUE SE REINICIA LA APLICACION 

___________________________________________________________________________
ESTRUCTURA DEL PROYECTO

PAQUETES
	com.globant.blockchain.controllers
		Contiene el controlador con los endpoint 
	
	com.globant.blockchain.model
		Contiene la Entidad Block con la estructura necesaria
		para contener la información de los bloques de la cadena
	
	com.globant.blockchain.process
		Contiene la clase BlockChain, esta clase maneja los recursos
		Metodos:
		
		"GenesisBlockChain": 	Inicializa el blockchain y se apoya de un metodo random que se encuentra en "Utils"
		
		"addBlock": 			Agrega un bloque a la cadena apoyandose en el metodo "miningBlock", este último 
								metodo se encarga de verificar que el HASH resultante empiece con "00"
		
		"validateBlock":		Revisa que la cadena sea congrunte en la información 
		
		"modifyBlockToInvalidate": Modifica un bloque del blockchain, si despues aplicas "validateBlock" el resultado
								   será un FALSE que indica que la cadena fue violada.
		
		com.globant.blockchain.utils
			Contiene utilerias HASH y escritura de archivos
		
###############################################################################		
							
							E N D  P O I N T S
							
###############################################################################
________________________________________________________
Agrega un nuevo bloque, el parametro es un String que sirve como mensaje
Verb: POST
http://localhost:8081/api/blockchain/addblock/{data}
________________________________________________________
Revisa la longitud del blockchain
Verb: GET
http://localhost:8081/api/blockchain/size
________________________________________________________
Muestra todos los bloques del blockchain
Verb: GET
http://localhost:8081/api/blockchain/show
________________________________________________________
Revisa si el blockchain es valido en cada bloque, verificando y
comparando los HASH
Verb: GET
http://localhost:8081/api/blockchain/validate
________________________________________________________
Modificar mensaje de un bloque especifico.
Puedes cambiar el mensaje y despues llamar a "validate" para
verificar si los valores del blockchain fueron alterados
Verb: Get
http://localhost:8081/api/blockchain/{data}/{position}

Ejemplo: 
Suponiendo que la cadena tiene al menos dos bloques
Cambia el valor del bloque en la posicion 1
	http://localhost:8081/api/blockchain/NuevoMensaje/1
Muestra las lista
	http://localhost:8081/api/blockchain/show
Valida que fue modificada
	http://localhost:8081/api/blockchain/validate

