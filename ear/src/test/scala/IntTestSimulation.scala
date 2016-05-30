import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class IntTestSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://kitchensink.apps.int.lin.eir.hu")
		.inferHtmlResources()



    val uri1 = "http://kitchensink.apps.int.lin.eir.hu"

	val scn = scenario("IntTestSimulation")
		.exec(http("request_0")
			.get("/"))
		.pause(1)
		.exec(http("request_1")
			.get("/index.jsf")
                        .check(substring("int-test-fail").notExists))
		.pause(30)
		.exec(http("request_2")
			.post("/index.jsf")
			.formParam("reg", "reg")
			.formParam("reg:name", "Jane Doe")
			.formParam("reg:email", "jdoe@user.com")
			.formParam("reg:phoneNumber", "12345678901")
			.formParam("reg:register", "Register"))
		.pause(20)
		.exec(http("request_3")
			.get("/rest/members"))
		.pause(20)
		.exec(http("request_4")
			.get("/index.jsf"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol).assertions(
          global.failedRequests.count.is(0)
        )
}


