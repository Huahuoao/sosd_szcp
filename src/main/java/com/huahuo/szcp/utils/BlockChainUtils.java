package com.huahuo.szcp.utils;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import java.security.SecureRandom;
import java.util.List;

public class BlockChainUtils {
    public static String generateBitcoinAddress() {
        NetworkParameters params = MainNetParams.get();
        try {
            // 生成随机的12个助记词种子
            byte[] entropy = new byte[16];
            new SecureRandom().nextBytes(entropy);
            DeterministicSeed seed = new DeterministicSeed(entropy, "", System.currentTimeMillis());

            // 从种子生成密钥链
            DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();

            // 定义路径
            List<ChildNumber> path = HDUtils.parsePath("M/44/0/0/0/0");

            // 获取指定路径下的密钥
            DeterministicKey key = chain.getKeyByPath(path, true);

            // 从私钥生成比特币地址
            ECKey ecKey = ECKey.fromPrivate(key.getPrivKey());
            LegacyAddress address = LegacyAddress.fromKey(params, ecKey);

            return address.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
