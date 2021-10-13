# Image_Encryption_Using_Blowfish_Algorithm
The project implements the idea of encrypting and decrypting the image using BLOWFISH algorithm. 
Blowfish is a symmetric block cipher that can be used as a replacement for DES or IDEA. 
It takes a variable-length key, from 32 bits to 448 bits and generates a sub key which can be 7168 bits long. 
After generating the sub key, data is encrypted by iteration of a function, 16 times (16 rounds). 
Each round contains a key-dependent permutation and key and data substitution. 
The blockSize is 64-bits with a variable keySize ranging from 32-bits to 448-bits. 
The number of subkeys is 18 [P-array]. There are 16 rounds of processing followed by post-processing. 
Four substitution boxes are used, each having 512 entries of 32-bits. 
The program employs usage of Base64 encoder to encrypt the key and prevent from snooping attack. 
Base64 encoder converts 24 bits of data into 4 base64 characters. 
The mapping to characters is done using a table.
