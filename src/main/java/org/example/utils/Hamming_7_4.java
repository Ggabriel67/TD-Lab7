package org.example.utils;

public class Hamming_7_4
{
    public static int[] encode(int[] data)
    {
        int[] encodedData = new int[7];

        encodedData[2] = data[0];
        encodedData[4] = data[1];
        encodedData[5] = data[2];
        encodedData[6] = data[3];

        encodedData[0] = encodedData[2] ^ encodedData[4] ^ encodedData[6];
        encodedData[1] = encodedData[2] ^ encodedData[5] ^ encodedData[6];
        encodedData[3] = encodedData[4] ^ encodedData[5] ^ encodedData[6];

        return encodedData;
    }

    public static int[] decode(int[] receivedData)
    {
        int[] decodedData = new int[4];

        int p1 = receivedData[0] ^ receivedData[2] ^ receivedData[4] ^ receivedData[6];
        int p2 = receivedData[1] ^ receivedData[2] ^ receivedData[5] ^ receivedData[6];
        int p3 = receivedData[3] ^ receivedData[4] ^ receivedData[5] ^ receivedData[6];

        int errorBit = p1 + (p2 * 2) + (p3 * 4) - 1;
        if (errorBit >= 0)
            receivedData[errorBit] = (1 - receivedData[errorBit]) % 2;

        decodedData[0] = receivedData[2];
        decodedData[1] = receivedData[4];
        decodedData[2] = receivedData[5];
        decodedData[3] = receivedData[6];

        return decodedData;
    }
}
