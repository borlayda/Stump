package groovy.hu.nullpointerexception.stump.model

class User {

<<<<<<< HEAD
	String id
    String username
=======
    String name
>>>>>>> 4bcde931ac4817798e116f6d702155ec751c3f13
    String password
    String email
    Role role

    def changePassword(oldPassWord, newPassword){
    	if (this.password != oldPassWord){
    		throw new RuntimeException()
    	}
    	this.password = newPassword
    }

    def addPermission(permission, value) {
    	this.permissions[permission] = value
    }

    def deletePermission(permission) {
    	this.permissions.remove(permission)
    }

}