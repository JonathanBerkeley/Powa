def main():
    print("Powa testing login logic\n")
    #These are not real logins :)
    logins = (
        Login(1, "My Google password", "google", "https://google.ie", "testpassword", "testusername@gmail.com"),
        Login(2, "My IADT password", "iadt", "https://iadt.ie", "iadtpassword123", "jonathanberkeley1999@gmail.com"),
        Login(3, "My passwordless/usernameless login", "test", "test_name"),
        Login(4, "My usernameless login", "test2", "test_name2", "testpassword123")
    )
    for l in logins:
        l.print_data()

    #General tests
    print("\nLogic testing")
    print(logins[1].get_instance().id)
    print(logins[2].get_username())

class Login:
    #req: self, id (internal), title, website/app addr/name.
    #opt: password, email/username

    #Constructors
    def __init__(self, id, title, target, target_name, password = None, username = None):
        self.id = id #id from db
        self.title = title
        self.target = target
        self.target_name = target_name
        self.password = password
        self.username = username

    def get_instance(self):
        return self

    def get_username(self):
        return self.username
    
    def print_data(self):
        print(" -- ", self.id, "|", self.title, "|", self.target, "|", self.target_name, "|", self.password, "|", self.username, " -- ")

if __name__ == "__main__":
    main()
