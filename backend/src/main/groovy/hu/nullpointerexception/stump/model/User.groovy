package hu.nullpointerexception.stump.model

class User {

    String name
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