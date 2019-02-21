import { Moment } from 'moment';
import { IPersonel } from 'app/shared/model/personel.model';

export const enum OdemeTuru {
    MAAS = 'MAAS',
    AVANS = 'AVANS',
    HIBE = 'HIBE',
    DIGER = 'DIGER'
}

export const enum OdemeYontemi {
    NAKIT = 'NAKIT',
    BANKA = 'BANKA',
    CEK = 'CEK',
    SENET = 'SENET',
    DIGER = 'DIGER'
}

export interface IPersonelOdeme {
    id?: number;
    tarih?: Moment;
    miktar?: number;
    turu?: OdemeTuru;
    yontem?: OdemeYontemi;
    personel?: IPersonel;
}

export class PersonelOdeme implements IPersonelOdeme {
    constructor(
        public id?: number,
        public tarih?: Moment,
        public miktar?: number,
        public turu?: OdemeTuru,
        public yontem?: OdemeYontemi,
        public personel?: IPersonel
    ) {}
}
