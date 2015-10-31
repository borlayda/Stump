package hu.nullpointerexception.stump.model

class User extends Entity {

	Long id
    String username
    String password
    Map<String, String> permissions = [:]
    String token
    String email
    String address

    def setUsername(name) {
    	this.username = name
    }

    def changePassword(oldPassWord, newPassword) {
    	if (this.password != oldPassWord){
    		throw Error("Wrong old password")
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