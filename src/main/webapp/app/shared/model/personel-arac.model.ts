import { Moment } from 'moment';
import { IArac } from 'app/shared/model/arac.model';
import { IPersonel } from 'app/shared/model/personel.model';

export interface IPersonelArac {
    id?: number;
    tarih?: Moment;
    detay?: any;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
    arac?: IArac;
    personel?: IPersonel;
}

export class PersonelArac implements IPersonelArac {
    constructor(
        public id?: number,
        public tarih?: Moment,
        public detay?: any,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public arac?: IArac,
        public personel?: IPersonel
    ) {}
}
