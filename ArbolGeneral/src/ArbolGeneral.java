 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose
 */
public class ArbolGeneral {
    NodoGeneral raiz;
    
    public ArbolGeneral(){
        raiz=null;
    }
    
    public boolean insertar(char dato, String path){
        /*
        1. raiz es null
        2. path vacio
        3. buscar padre
        4. crear hijo
        5. enlazar padre con hijo
        */
        if(raiz==null){
            raiz=new NodoGeneral(dato);
            if(raiz==null) return false;
            return true;
        }
        if(path.isEmpty()) return false;
        
        NodoGeneral padre=buscarNodo(path);
        if(padre==null) return false;
        
        //revisar si ya existe un hijo con la misma letra que deseo insertar
        NodoGeneral hijoYaExiste=buscarNodo(path+"/"+dato);
        if(hijoYaExiste!=null) return false;
        
        NodoGeneral nuevo=new NodoGeneral(dato);
        return padre.enlazar(nuevo);
    }

    private NodoGeneral buscarNodo(String path) {
        //-> /F/W/M/R
        path=path.substring(1);
        //-> F/W/M/R
        String vector[]=path.split("/");
        int cansado = 1;
        if(vector[0].charAt(0)==raiz.dato){
            //el vector solo contiene una letra es decir solo hay raiz como padre
            if(vector.length==1) return raiz;
            NodoGeneral padre = raiz;
            return buscarNodo(padre, vector, cansado);
        }
        return null; //no coincide celda 0 con raiz
    }
    
    private NodoGeneral buscarNodo(NodoGeneral padre, String[] vector, int cansado){
        if(cansado<vector.length){      
            padre = padre.obtenerHijo(vector[cansado].charAt(0));
            if(padre == null) return null;
            cansado=cansado+1;
            padre = buscarNodo(padre, vector, cansado);
        }
        return padre;
    }
   
    public boolean eliminar(String path){
        if(raiz==null) return false;
        
        NodoGeneral hijo = buscarNodo(path);
        if(hijo==null) return false;
        
        if(!hijo.esHoja()) return false;
        
        if(hijo==raiz){
            raiz = null;
            return true;
        }
        String pathPadre = obtenerPathPadre(path);
        NodoGeneral padre = buscarNodo(pathPadre);
        
        return padre.desenlazar(hijo);
    }  

    private String obtenerPathPadre(String path) {
        int posicionANTESUltimaDiagonal = path.lastIndexOf("/")-1;
        return path.substring(0,posicionANTESUltimaDiagonal);
    }
    
}
