package cn.xdaoy.utils;


public class MathUtils {

	/**
	 * group function
	 * @param length，total size
	 * @param percent，max size percent
	 * @return
	 */
	public static long[] group(final long length, final long percent) {
		long c = length / percent;
		long left = length % percent;
		int size = (int) (left == 0 ? c : c + 1);
		long[] group = new long[size];
		int i = 0;
		while (i < size - 1) {
			group[i] = percent;
			i++;
		}
		group[size - 1] = left == 0 ? percent : left;
		return group;
	}
	
}
