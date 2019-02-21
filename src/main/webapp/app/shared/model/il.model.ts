export interface IIl {
    id?: number;
    ad?: string;
}

export class Il implements IIl {
    constructor(public id?: number, public ad?: string) {}
}
