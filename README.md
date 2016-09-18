# ruid

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7bc6ce8fd90a44d59f398b1b4f39bba1)](https://www.codacy.com/app/nfischer921/ruid?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kag0/ruid&amp;utm_campaign=Badge_Grade)  
[![Build Status](https://travis-ci.org/kag0/ruid.svg?branch=master)](https://travis-ci.org/kag0/ruid)  
[![](https://jitpack.io/v/kag0/ruid.svg)](https://jitpack.io/#kag0/ruid)  
[![codecov](https://codecov.io/gh/kag0/ruid/branch/master/graph/badge.svg)](https://codecov.io/gh/kag0/ruid)  

RUIDs are uniformly distributed, are 24 URI-safe ASCII characters when encoded,
and are unique by 18 random bytes. They look like this: `9uZIuRrbPRl71PiRAmUID9xd`.

## Where?

Anywhere you need a non-sequential unique id.

## How?

```java
RUID myRuid = RUID.generate();
RUID parsed = RUID.parse("9uZIuRrbPRl71PiRAmUID9xd");
byte[] binary = myRuid.bytes();
String encoded = myRuid.toString();
```

A RUID is 18 random bytes, [Base64Url](https://tools.ietf.org/html/rfc4648#section-5) 
encoded when needed in text form. It can be stored or transmitted in its binary 
for speed and efficiency, or encoded for use in text-based protocols.

## Why not UUID?

UUIDs require the ability to determine the version of the UUID, and then verify 
the structure of the UUID against that version. RUIDs have no such structure and 
simply require decoding using base64url which is fast, well known, and widely 
available in most languages.  
UUIDs also do not truncate well. Removing characters from the beginning or end of 
a UUID can at best disproportionately decrease the uniqueness (with respect to 
the number of characters removed), and at worst completely remove uniqueness. 
RUIDs have no such issues, removing characters removes proportional uniqueness, 
regardless of where in the RUID the character was removed from.

###  Version 1

* relies on unique MAC addresses for every device in the system, this is 
unsuitable for interoperable systems where you don't control every device
* requires complexity in UUID-generating code to prevent duplicate generation in same timestep
* exposes MAC of creating machine and time of creation

### Version 3

Deprecated in favor of version 5

### Version 4 

Version 4 UUIDs are the most common and widely used version. They use about 15 
bytes of random data to achieve uniqueness, but require 16 bytes to store. 
Due to their hex encoding and part separation, they take 36 ASCII characters to display when encoded.  
RUIDs on the other hand are only 2/3 the size of UUIDv4 when encoded, yet describe 22 bits more.

### Version 5

Only useful in identifying named resources, not as database key, nonce, or other such use.

