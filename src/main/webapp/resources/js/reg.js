var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');
document.getElementsByClassName("forDrivers").hidden=true;



class MyForm extends React.Component {
    constructor() {
        super();
        this.state = {role: ""};

        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChangeRole = this.onChangeRole.bind(this);

    }

    onChangeRole(e) {

        var val = e.target.value;
        this.setState({role: val});
        if (val == "ROLE_DRIVER") {
            document.getElementById('ifDriver').style.display = 'block';
        } else {
            document.getElementById('ifDriver').style.display = 'none';
        }
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        data.forEach((value, key) => {data[key] = value});
        console.log(data.name);
        var user = {
            email: data.email,
            password:data.password,
            userRole: data.userRole
        };

        $.ajax({
            url: CONTEXT_PATH + '/user/add',
            type: "POST",
            data: JSON.stringify(user),
            contentType:' application/json',
            success: function () {
                if (data.userRole == "ROLE_DRIVER") {

                    var driver = {
                        name: data.name,
                        surname: data.surname,
                        status: data.status,
                        city_id: data.city
                    };

                    fetch(CONTEXT_PATH + '/driver/registerAdd/' + user.email, {
                        method: 'POST',
                        headers: {
                            'Content-Type': ' application/json'
                        },
                        body: JSON.stringify(driver)
                    });

                }
            }
        });

    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label htmlFor="email">Enter email</label>
                <input id="email" name="email" type="text" />

                <label htmlFor="password">Enter your password</label>
                <input id="password" name="password" type="password" />
                <select name="userRole" value={this.state.role} onChange={this.onChangeRole}>
                    <option value="ROLE_EMPLOYEE">Employee</option>
                    <option value="ROLE_DRIVER">Driver</option>
                </select>
<div id="ifDriver" style={{display:'none'}}>
                <label className="forDrivers">Name</label>
                <input className="forDrivers" name="name" type="text" />
                <label className="forDrivers" >Surname</label>
                <input className="forDrivers" name="surname" type="text" />
                <label className="forDrivers">Status</label>
                <select className="forDrivers" name="status" >
                    <option value="rest">Rest</option>
                    <option value="shift">Shift</option>
                    <option value="behindTheWheel">Behind the wheel</option>
                </select>

                <label className="forDrivers">City</label>
                <select className="forDrivers" id="idCities" name="city" >

                </select>
</div>

                <button>CREATE ACCOUNT</button>
            </form>
        );

    }
}

ReactDOM.render(<MyForm />, document.getElementById('app'));

$.ajax({
    type: "GET",
    url: CONTEXT_PATH + "/cities/listCitiesDTO",
    success: function(data){
        console.log(data);
         $.each(data, function(i, d) {
             $('#idCities').append('<option value="' + d.id + '">' + d.name + '</option>');
         });
    }
});

