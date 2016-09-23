# PeakInvestigator-Java-SDK

A Java library for interacting with the PeakInvestigator™ public API (https://peakinvestigator.veritomyx.com/api).

# Using the PeakInvestigatorSaaS library

The easiest way to add this library to your project is to pull it in via Maven:

```
<dependency>
  <groupId>com.veritomyx</groupId>
  <artifactId>peakinvestigator-sdk</artifactId>
  <version>4.0</version>
</dependency>
```

The general use is to create an instance of PeakInvestigatorSaaS, as well as instances of various "Actions" corresponding 
to the desired API calls to PeakInvestigator (see https://peakinvestigator.veritomyx.com/api for complete list), and call 
executeAction() on the desired action. For example, making a call to the PI_VERSIONS API can be accomplished 
with the following code:

```

import com.veritomyx.PeakInvestigatorSaaS;
import com.veritomyx.actions.*;

...

PeakInvestigatorSaaS webService = new PeakInvestigatorSaaS("peakinvestigator.veritomyx.com");
PiVersionsAction action = new PiVersionsAction("3.6", "username", "password");
String response = webService.executeAction(action);
action.processResponse(response);
String[] versions = action.getVersions();

...

```

## Building

Maven is used for satisfying depdendencies and packaging a jar. This is as simple as executing the following command:

```
mvn package
```

# Additional Information

For more information, including additional help with building and using the PeakInvestigtorSaaS library, contact support@veritomyx.com.
