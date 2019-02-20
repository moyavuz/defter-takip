import { Moment } from 'moment';
import { IPersonel } from 'app/shared/model/personel.model';

export const enum IzinTuru {
    YILLIK_IZIN = 'YILLIK_IZIN',
    UCRETSIZ_IZIN = 'UCRETSIZ_IZIN',
    SAGLIK_RAPORLU = 'SAGLIK_RAPORLU',
    EVLILIK_IZNI = 'EVLILIK_IZNI',
    DOGUM_IZNI = 'DOGUM_IZNI'
}

export interface IPersonelIzin {
    id?: number;
    tarih?: Moment;
    miktar?: number;
    turu?: IzinTuru;
    dosyaContentType?: string;
    dosya?: any;
    personel?: IPersonel;
}

export class PersonelIzin implements IPersonelIzin {
    constructor(
        public id?: number,
        public tarih?: Moment,
        public miktar?: number,
        public turu?: IzinTuru,
        public dosyaContentType?: string,
        public dosya?: any,
        public personel?: IPersonel
    ) {}
}
